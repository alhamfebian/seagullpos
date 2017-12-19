package com.blibli.seagullpos.service;

import com.blibli.seagullpos.dao.MemberDAO;
import com.blibli.seagullpos.model.MemberModel;
import com.blibli.seagullpos.model.SummaryList;
import com.blibli.seagullpos.utility.RandomId;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.SecureRandom;
import java.util.List;

@WebServlet("/member/*")
public class MemberService extends HttpServlet {

    private MemberDAO dao = null;


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        dao = new MemberDAO();

        MemberModel member = new MemberModel();
        String fullName = request.getParameter("fullname");
        String memberEmail = request.getParameter("memberEmail");
        String memberPhoneNumber = request.getParameter("memberPhoneNumber");
        long newId = RandomId.generateRandomNumber(16);

        member.setMemberID(newId);
        member.setMemberName(fullName);
        member.setMemberEmail(memberEmail);
        member.setPhoneNumber(memberPhoneNumber);
        member.setMemberPoint(0);

        dao.insertMember(member);
    }



    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        dao = new MemberDAO();
        PrintWriter pw = response.getWriter();
        SummaryList<MemberModel> memberSummaryList = null;
        int currentPage = 1;
        currentPage = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : currentPage;
        if(request.getPathInfo() == null){
            memberSummaryList = dao.getMemberSummaryList(currentPage);
        }else if(request.getPathInfo().equals("/search")){
            String query = request.getParameter("query");
            memberSummaryList = dao.getMemberSearchSummaryList(query, currentPage);
        }

        GsonBuilder builder = new GsonBuilder();
        builder.serializeNulls();
        builder.setPrettyPrinting();

        Gson gson = builder.create();

        String listMemberJSON = gson.toJson(memberSummaryList);

        pw.write(listMemberJSON);
    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        dao = new MemberDAO();

        BufferedReader br = request.getReader();
        String json = br.readLine();
        MemberModel member = new Gson().fromJson(json, MemberModel.class);

        dao.updateMember(member);
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        dao = new MemberDAO();
        long deleteId = Long.parseLong(request.getParameter("id"));
        dao.deleteMember(deleteId);
    }
}
