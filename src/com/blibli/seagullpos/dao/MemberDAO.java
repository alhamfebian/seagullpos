package com.blibli.seagullpos.dao;

import com.blibli.seagullpos.connection.ConnectionManager;
import com.blibli.seagullpos.model.HeaderTransactionModel;
import com.blibli.seagullpos.model.MemberModel;
import com.blibli.seagullpos.model.SummaryList;

import java.sql.*;
import java.util.*;

public class MemberDAO {
    private Connection connection = null;
    private PreparedStatement ps = null;

    private SummaryList<MemberModel> memberSummaryList = null;
    private List<MemberModel> listMember = null;

    public List<MemberModel> getPaginateMemberList(int offset, int limit){
        String query = "SELECT m.*, COUNT(transactionid) AS total " +
                "FROM member m " +
                "LEFT JOIN headertransaction hr " +
                "ON m.memberid = hr.memberid " +
                "GROUP BY m.memberid " +
                "ORDER BY total DESC " +
                "LIMIT ? OFFSET ?";
        try{
            connection = ConnectionManager.createConnection();
            ps = connection.prepareStatement(query);
            ps.setInt(1, limit);
            ps.setInt(2, offset);
            ResultSet rs = ps.executeQuery();
            listMember = processAlLRowData(rs);
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            if(connection != null) ConnectionManager.closeConnection(connection);
            if(ps != null){
                try {
                    ps.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
        return listMember;
    }

    public int getTotalData(){
        String query = "SELECT COUNT(*) FROM member";
        int totalData = 0;
        try{
            connection = ConnectionManager.createConnection();
            ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                totalData = rs.getInt(1);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            if(connection != null) ConnectionManager.closeConnection(connection);
            if(ps != null){
                try{
                    ps.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
        return totalData;
    }

    public boolean insertMember(MemberModel member){
        String query = "INSERT INTO " +
                "Member (memberid, membername, memberpoint, memberemail, phonenumber, registerdate) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        boolean insertStatus = true;
        try{
            connection = ConnectionManager.createConnection();
            ps = connection.prepareStatement(query);
            Timestamp newTime = new Timestamp(System.currentTimeMillis());
            ps.setLong(1, member.getMemberID());
            ps.setString(2, member.getMemberName());
            ps.setInt(3, member.getMemberPoint());
            ps.setString(4, member.getMemberEmail());
            ps.setString(5, member.getPhoneNumber());
            ps.setTimestamp(6, newTime);

            insertStatus = ps.execute();

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            if(connection != null) ConnectionManager.closeConnection(connection);
            if(ps != null){
                try{
                    ps.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }

        return insertStatus;
    }

    public List<MemberModel> liveSearch(String search, int offset, int limit){
        String query = "SELECT m.*, COUNT(transactionid) AS total " +
                "FROM member m " +
                "LEFT JOIN headertransaction hr " +
                "ON m.memberid = hr.memberid " +
                "WHERE m.memberid::text LIKE ? OR LOWER(m.membername) LIKE LOWER(?) OR m.phonenumber LIKE ? " +
                "GROUP BY m.memberid " +
                "ORDER BY total DESC " +
                "LIMIT ? OFFSET ?";

        try{
            connection = ConnectionManager.createConnection();
            ps = connection.prepareStatement(query);
            ps.setString(1, "%" + search + "%");
            ps.setString(2, "%" + search + "%");
            ps.setString(3, "%" + search + "%");
            ps.setInt(4, limit);
            ps.setInt(5, offset);

            ResultSet rs = ps.executeQuery();

            listMember = processAlLRowData(rs);

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            if(connection != null) ConnectionManager.closeConnection(connection);
            if(ps != null){
                try {
                    ps.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
        return listMember;
    }

    public int getTotalSearchData(String search){
        String query = "SELECT COUNT(*) FROM member WHERE memberid::text LIKE ? OR LOWER(membername) LIKE LOWER(?) OR phonenumber LIKE ?";
        int total = 0;
        try{
            connection = ConnectionManager.createConnection();
            ps = connection.prepareStatement(query);

            ps.setString(1, "%" + search + "%");
            ps.setString(2, "%" + search + "%");
            ps.setString(3, "%" + search + "%");

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                total = rs.getInt(1);
            }


        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            if(connection != null) ConnectionManager.closeConnection(connection);
            if(ps != null) {
                try{
                    ps.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
        return total;
    }

    public boolean updateMember(MemberModel member){
        String query = "UPDATE member " +
                "SET " +
                "membername = ?, " +
                "memberemail = ?, " +
                "phonenumber = ? " +
                "WHERE memberid = ?";
        boolean updateStatus = true;
        try{
            connection = ConnectionManager.createConnection();
            ps = connection.prepareStatement(query);

            ps.setString(1, member.getMemberName());
            ps.setString(2, member.getMemberEmail());
            ps.setString(3, member.getPhoneNumber());
            ps.setLong(4, member.getMemberID());
            updateStatus = ps.execute();

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            if(connection != null) ConnectionManager.closeConnection(connection);
            if(ps != null) {
                try {
                    ps.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
        return updateStatus;
    }

    public boolean deleteMember(long memberId){
        String query = "DELETE FROM member WHERE memberid = ?";
        boolean deleteStatus = true;
        try{
            connection = ConnectionManager.createConnection();
            ps = connection.prepareStatement(query);
            ps.setLong(1, memberId);
            deleteStatus = ps.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            if(connection != null) ConnectionManager.closeConnection(connection);
            if(ps != null) {
                try{
                    ps.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
        return  deleteStatus;
    }

    private List<MemberModel> processAlLRowData(ResultSet rs) throws SQLException{
        List<MemberModel> listMember = new ArrayList<>();

        while (rs.next()){
            MemberModel member = new MemberModel();
            member.setMemberID(rs.getLong("memberid"));
            member.setMemberName(rs.getString("membername"));
            member.setMemberPoint(rs.getInt("memberpoint"));
            member.setMemberEmail(rs.getString("memberemail"));
            member.setPhoneNumber(rs.getString("phonenumber"));
            member.setRegisterDate(rs.getTimestamp("registerdate"));
            member.setTotalTransaction(rs.getInt("total"));

            listMember.add(member);
        }
        return listMember;
    }

    public SummaryList<MemberModel> getMemberSummaryList(int currentPage){
        int limit = 10;
        int offset = (currentPage - 1) * 10;
        List<MemberModel> members = getPaginateMemberList(offset, limit);
        int totalMember = getTotalData();
        setEmployeeSummaryData(members, currentPage, limit, totalMember);
        return memberSummaryList;
    }

    public void setEmployeeSummaryData(List<MemberModel> members, int currentPage, int limit, int totalData) {
        memberSummaryList = new SummaryList<>();
        memberSummaryList.setContent(members);
        memberSummaryList.setCurrentPage(currentPage);
        memberSummaryList.setPageSize(limit);
        memberSummaryList.setTotalRecords(totalData);
    }

    public SummaryList<MemberModel> getMemberSearchSummaryList(String query, int currentPage){
        int limit = 10;
        int offset = (currentPage - 1) * 10;
        List<MemberModel> members = liveSearch(query, offset, limit);
        int totalMember = getTotalSearchData(query);
        setEmployeeSummaryData(members, currentPage, limit, totalMember);
        return memberSummaryList;
    }
}
