<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<spring:url value="/resources/images/ko.png" var="crunchifyJS" />
<spring:url value="/resources/search.png" var="img" />
<spring:url value="/resources/images/plus.gif" var="imgtwo" />
<spring:url value="/resources/images/minus.gif" var="imgthree" />

<spring:url value="/resources/JS/jquery.min.js" var="jsone" />
<spring:url value="/resources/JS/ddaccordion.js" var="jstwo" />
<spring:url value="/resources/JS/jconfirmaction.jquery.js" var="jsthree" />
<spring:url value="/resources/JS/niceforms.js" var="jsfour" />

<spring:url value="/resources/CSS/style.css" var="style" />
<spring:url value="/resources/CSS/niceforms-default.css" var="styleform" />

<spring:url value="editevent" var="editpage" />
<spring:url value="notify" var="notifypage" />
<spring:url value="leaderboard" var="leaderboardpage" />


<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Kony EVENTS | ADMIN PANEL</title>
<style type="text/css">
    <%@include file="/resources/CSS/style.css" %>
    </style>

<script type="text/javascript"
	src="${jsone}"></script>
<script type="text/javascript"
	src="${jstwo}"></script>
<script type="text/javascript">
	ddaccordion.init({
		headerclass : "submenuheader", //Shared CSS class name of headers group
		contentclass : "submenu", //Shared CSS class name of contents group
		revealtype : "click", //Reveal content when user clicks or onmouseover the header? Valid value: "click", "clickgo", or "mouseover"
		mouseoverdelay : 200, //if revealtype="mouseover", set delay in milliseconds before header expands onMouseover
		collapseprev : true, //Collapse previous content (so only one open at any time)? true/false 
		defaultexpanded : [], //index of content(s) open by default [index1, index2, etc] [] denotes no content
		onemustopen : false, //Specify whether at least one header should be open always (so never all headers closed)
		animatedefault : false, //Should contents open by default be animated into view?
		persiststate : true, //persist state of opened contents within browser session?
		toggleclass : [ "", "" ], //Two CSS classes to be applied to the header when it's collapsed and expanded, respectively ["class1", "class2"]
		togglehtml : [ "suffix",
				"<img src='${imgtwo}' class='statusicon' />",
				"<img src='${imgthree}' class='statusicon' />" ], //Additional HTML added to the header when it's collapsed and expanded, respectively  ["position", "html1", "html2"] (see docs)
		animatespeed : "fast", //speed of animation: integer in milliseconds (ie: 200), or keywords "fast", "normal", or "slow"
		oninit : function(headers, expandedindices) { //custom code to run when headers have initalized
			//do nothing
		},
		onopenclose : function(header, index, state, isuseractivated) { //custom code to run whenever a header is opened or closed
			//do nothing
		}
	})
</script>

<script type="text/javascript"
	src="${jsthree}"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$('.ask').jConfirmAction();
	});
</script>


<script language="javascript" type="text/javascript"
	src="${jsfour}"></script>
<style type="text/css">
    <%@include file="/resources/CSS/niceforms-default.css" %>
    </style>
</head>
<body>

	<div id="main_container">

		<div class="header">
			<div class="logo">
				<a href="#"><img src="${crunchifyJS}"
					alt="" title="" border="0" /></a>
			</div>

			<div class="right_header">
				Welcome Admin, | <a href="#" class="logout">Logout</a>
			</div>
			<div class="jclock"></div>
		</div>

		<div class="main_content">

			<div class="menu">
				<ul>
					<li><a  href="createevent">Admin Home</a></li>
					<li><a class="current" href="${leaderboardpage }">LEADERBOARD</a></li>
					<li><a href="">EVENTS<!--[if IE 7]><!--></a>
					<!--<![endif]--> <!--[if lte IE 6]><table><tr><td><![endif]-->
						<ul>
							<li><a href="createevent" title="">CREATE EVENT</a></li>
							<li><a href="${editpage}" title="">EDIT EVENT</a></li>
							<li><a href="${notifypage}" title="">SEND NOTIFICATIONS</a></li>
							<li><a href="" title="">HISTORY</a></li>

						</ul> <!--[if lte IE 6]></td></tr></table></a><![endif]--></li>
			</div>

			<div class="center_content">



				<div class="left_content">

					<div class="sidebar_search">
						<form>
							<input type="text" name="" class="search_input"
								value="search keyword" onclick="this.value=''" /> <input
								type="image" class="search_submit" src="${img}" />
						</form>
					</div>

				</div>
				<div class="right_content">
					<h2 align="center">LEADERBOARD</h2>
					<table id="rounded-corner" >
    <thead>
    	<tr>
        	<th scope="col" class="rounded-company"></th>
            <th scope="col" class="rounded">Candidate Name</th>
            <th scope="col" class="rounded">ID</th>
            <th scope="col" class="rounded">Points </th>
            <th scope="col" class="rounded">Rank</th>
                  </tr>
    </thead>
        
    <tbody>
    	<tr>
        	<td><input type="checkbox" name="" /></td>
            <td id="Candidatename" value="Candidate Name">Candidate Name</td>
            <td id="CandidateID">KH9279</td>
            <td>---</td>
            <td>---</td></tr></tbody></table>
					
				</div>
				<!-- end of right content-->
			</div>
			<!--end of center content -->

			<div class="clear"></div>
		</div>
		<!--end of main content-->



	</div>
</body>
</html>