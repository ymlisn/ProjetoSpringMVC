package com.tutorial.spring.custom;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import org.apache.commons.lang3.StringUtils;

import com.tutorial.spring.utils.PaginationUtils;

public class PaginationTag implements Tag {
private PageContext pageContext;
private Tag parentTag;
private String action;
private int totalRecords;
private int recordsPerPage = PaginationUtils.RECORDS_PER_PAGE;
private int totalPageCount;
private int viewPageCount = PaginationUtils.VIEW_PAGE_COUNT;
private int startIndx = 1;
private int endIndx = viewPageCount;

public void setPageContext(PageContext pageContext) {
 this.pageContext = pageContext;
}

public void setParent(Tag parentTag) {
 this.parentTag = parentTag;
}

public void setTotalPageCount(int totalRecords) {
 this.totalPageCount = PaginationUtils.getTotalPages(totalRecords, recordsPerPage);
}

public void setAction(String action) {
 this.action = action;
}

public void setRecordsPerPage(int recordsPerPage) {
	this.recordsPerPage = recordsPerPage;
}

public void setViewPageCount() {
 this.viewPageCount = PaginationUtils.getTotalViewPageCount(totalPageCount);
}

public void setTotalRecords(int totalRecords) {
	this.totalRecords = totalRecords;
	this.viewPageCount = PaginationUtils.RECORDS_PER_PAGE;
	setTotalPageCount(totalRecords);
	setViewPageCount();
}

public int doStartTag() throws JspException {
 if(viewPageCount<1) {
 }
 return Tag.SKIP_BODY;
}
 
public int doEndTag() throws JspException {
 if(this.totalPageCount<1) {
	 return Tag.SKIP_PAGE;
 }
 ServletRequest request = pageContext.getRequest();
 String page = request.getParameter("page");
 if(page != null) {

	 if(!StringUtils.isNumeric(page) || Integer.parseInt(page) <= 0) {
		 page = String.valueOf(1);
	 }
	 if(Integer.parseInt(page) > totalPageCount) {
		 page = String.valueOf(totalPageCount);
	 }
 }
 JspWriter out = pageContext.getOut();
 this.endIndx = this.startIndx + this.viewPageCount - 1;
 try {
  if(page!=null && Integer.parseInt(page) > endIndx) {
   if(Integer.parseInt(page)==totalPageCount) {
    startIndx = totalPageCount-viewPageCount+1;
   } else {
    startIndx = startIndx+1;     
   }
   endIndx = Integer.parseInt(page);
  }
  if(page!=null && Integer.parseInt(page) < startIndx) {
   startIndx = Integer.parseInt(page);
   if(Integer.parseInt(page)==1) {
    endIndx = viewPageCount;
   } else {
    endIndx = endIndx-1;     
   }
  }
  if(startIndx>1) {
   out.write(getLink(this.action, 1, false, "<<"));
   out.write(getLink(this.action, (Integer.parseInt(page)-1), false, "<"));
  }
  for(int i=startIndx; i<=endIndx; i++) {
   if((page==null && i==1) || (page != null && i==(Integer.parseInt(page)))) {
    out.write(getLink(this.action, i, true, String.valueOf(i))); 
   } else {
    out.write(getLink(this.action, i, false, String.valueOf(i)));     
   }
  }
  if(endIndx<this.totalPageCount) {
   out.write(getLink(this.action, (page!=null)?(Integer.parseInt(page)+1):2, false, ">"));
   out.write(getLink(this.action, this.totalPageCount, false, ">>"));
  }
  out.write(getTotalRecordsInPagesLabel());
  out.flush();
 } catch (IOException e) {
  e.printStackTrace();
  throw new JspException(e);
 }
 return Tag.EVAL_PAGE;
}

/**
 * @param action
 * @param page
 * @param isCurrentPage
 * @param desc
 * @return
 */
private String getLink(final String action, final int page, final boolean isCurrentPage, final String desc) {
 StringBuilder link = new StringBuilder();
 link.append("<a href='");
 link.append(action);
 link.append("?page=");
 link.append(page);
 link.append("'><font color='"+(isCurrentPage ? "red" : "blue")+"'>");
 link.append(desc);
 link.append("</font></a>&nbsp;");
 return link.toString();
}

private String getTotalRecordsInPagesLabel() {
	StringBuilder link = new StringBuilder();
	String label = totalRecords + " Resultados totais em " + totalPageCount + " páginas";
	 link.append("<span class=\"custom-pagination-total-records\">" + label + "</span>");
	 return link.toString();
}

public Tag getParent() {
 return null;
}

public void release() {
 
}
}