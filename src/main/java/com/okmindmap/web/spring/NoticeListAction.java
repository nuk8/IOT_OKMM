package com.okmindmap.web.spring;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.okmindmap.model.Notice;
import com.okmindmap.service.NoticeService;

public class NoticeListAction extends BaseAction {

	private NoticeService noticeService;
	
	public void setNoticeService(NoticeService noticeService) {
		this.noticeService = noticeService;
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> data = new HashMap<String, Object>();
		
		String keyword = request.getParameter("keyword");
		String bbs_gb = request.getParameter("bbs_gb");
		
		int page =getOptionalParam(request, "page", 1);
		int sizePerPage = 10;
		
		List<Notice> noticeList =  noticeService.noticeList(keyword, page, sizePerPage,bbs_gb);
		int listCount = noticeService.noticeListCount(keyword,bbs_gb);
		
		data.put("noticeList", noticeList);
		
		int pageCount = listCount/sizePerPage +1;
		data.put("listCount", listCount);
		data.put("pageCount", pageCount);
		
		int pagedGroup = 10;
		int nPage = new Integer(page).intValue();
		int pagedLoop = pagedGroup;
		if (pagedLoop > pageCount) {
			pagedLoop = pageCount;	
		}
		String deli = new String(" ");	
		StringBuffer sb = new StringBuffer();
		
		if ((pageCount > pagedGroup) && (nPage > pagedGroup)) {
			sb.append("<a href=\"#\" onclick=\"setPageNumber('"+(nPage - pagedGroup) +"');\"");
			sb.append("\">");
			sb.append("[" + pagedGroup + "]");
			sb.append("</a>");
			sb.append(deli);
		}
		
		for (int i = 0; i < pagedLoop; i++) {
			sb.append("<a href=\"#\" onclick=\"setPageNumber('"+(i + 1) +"');\"");
			sb.append("\">");
			sb.append(i + 1);
			sb.append("</a>");
			sb.append(deli);
		}
		
		if ((pageCount > pagedGroup) && (nPage < pagedGroup)) {
			sb.append("<a href=\"#\" onclick=\"setPageNumber('"+(nPage + pagedGroup) +"');\"");
			sb.append("\">");
			sb.append("[" + pagedGroup + "]");
			sb.append("</a>");
		}
		data.put("pagedLink", sb.toString());
		
		return new ModelAndView("../noticeList", "data", data);
	}

}
