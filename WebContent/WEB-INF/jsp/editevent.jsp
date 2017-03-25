<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<spring:url value="/resources/images/ko.png" var="crunchifyJS" />
<spring:url value="/resources/search.png" var="img" />
<spring:url value="/resources/images/plus.gif" var="imgtwo" />
<spring:url value="/resources/images/minus.gif" var="imgthree" />
<spring:url value="/resources/images/user_edit.png" var="imgfour"/>
<spring:url value="/resources/images/trash.png" var="imgfive"/>


<spring:url value="/resources/JS/jquery.min.js" var="jsone" />
<spring:url value="/resources/JS/ddaccordion.js" var="jstwo" />
<spring:url value="/resources/JS/jconfirmaction.jquery.js" var="jsthree" />
<spring:url value="/resources/JS/niceforms.js" var="jsfour" />

<spring:url value="/resources/CSS/style.css" var="style" />
<spring:url value="/resources/CSS/niceforms-default.css" var="styleform" />

<spring:url value="editevent" var="editpage" />
<spring:url value="notify" var="notifypage" />
<spring:url value="leaderboard" var="leaderboardpage" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<style>
/* The Modal (background) */
.modal {
    display: none; /* Hidden by default */
    position: fixed; /* Stay in place */
    z-index: 1; /* Sit on top */
    padding-top: 100px; /* Location of the box */
    left: 0;
    top: 0;
    width: 100%; /* Full width */
    height: 100%; /* Full height */
    overflow: auto; /* Enable scroll if needed */
    background-color: rgb(0,0,0); /* Fallback color */
    background-color: rgba(0,0,0,0.4); /* Black w/ opacity */
}

/* Modal Content */
.modal-content {
    background-color: #fefefe;
    margin: auto;
    padding: 20px;
    border: 1px solid #888;
    width: 80%;
}

/* The Close Button */
.close {
    color: #aaaaaa;
    float: right;
    font-size: 28px;
    font-weight: bold;
}

.close:hover,
.close:focus {
    color: #000;
    text-decoration: none;
    cursor: pointer;
}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Kony EVENTS | ADMIN PANEL</title>
<style type="text/css">
    <%@include file="/resources/CSS/style.css" %>
    </style>
<link rel="stylesheet" type="text/css" href="style.css" />
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
					<li><a href="${leaderboardpage }">LEADERBOARD</a></li>
					<li><a class="current" href="">EVENTS<!--[if IE 7]><!--></a>
					<!--<![endif]--> <!--[if lte IE 6]><table><tr><td><![endif]-->
						<ul>
							<li><a href="createevent" title="">CREATE EVENT</a></li>
							<li><a href="${editpage }" title="">EDIT EVENT</a></li>
							<li><a href="${notifypage }" title="">SEND NOTIFICATIONS</a></li>
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
<dl>
                        <dt><label for="gender">Select Event:</label></dt>
                        <dd>
                            <select size="1" name="gender" id="">
                                <option value="" id="Event1">Select option 1</option>
                                <option value="">Select option 2</option>
                                <option value="">Select option 3</option>
                                <option value="">Select option 4</option>
                                <option value="">Select option 5</option>
                            </select>
                        </dd>
                    </dl>	
        
    
           
     <table id="rounded-corner" >
    <thead>
    	<tr>
        	<th scope="col" class="rounded-company"></th>
            <th scope="col" class="rounded">Event</th>
            <th scope="col" class="rounded">Description</th>
            <th scope="col" class="rounded">Start Date </th>
            <th scope="col" class="rounded">End Date</th>
            <th scope="col" class="rounded">Edit</th>
            <th scope="col" class="rounded-q4">Delete</th>
        </tr>
    </thead>
        
    <tbody>
    	<tr>
        	<td><input type="checkbox" name="" /></td>
            <td id="Eventname">Event name</td>
            <td id="Desc">Description</td>
            <td>---</td>
            <td>---</td>


            <td><button id="myBtn" onclick="modify()">
    <img src=" ${imgfour} "/></button> </td>
            <td><a href="#" class="ask"><img src="${imgfive}" alt="" title="" border="0" /></a></td>
        </tr>
        
        
    </tbody>
   
</table>
<div id="myModal" class="modal">

  <!-- Modal content -->
  <div class="modal-content">
    <span class="close">&times;</span>
    <h2 align="center">Modify Your Event</h2>
    
						<form action="/EnablementAppWeb/editEvent" method="post"
							class="niceform" id="myform">
    <fieldset>
								<dl><dt>
									
										<label for="name"> Name of the Event </label>	</dt></dl>
																		
										<input type="text" name="event_name" id="name"></input>
									<dl><dt>
									
										<label for="Description"> Description </label>		</dt>	</dl>				
									
										<input type="text" name="event_description" id="description"></input></dl>
										
																
													</dt>
													dl><dt>
									
										<label for="Location"> Location </label>		</dt>	</dl>				
									
										<input type="text" name="event_location" id="location"></input></dl>
										
																
													</dt>
												</dl>
									</fieldset>
									</form>
					
  </div>

</div>

    	  
 <script>
// Get the modal
function modify(){
var modal = document.getElementById('myModal');

// Get the button that opens the modal
var btn = document.getElementById("myBtn");

// Get the <span> element that closes the modal
var span = document.getElementsByClassName("close")[0];

// When the user clicks the button, open the modal 
btn.onclick = function() {
    modal.style.display = "block";
}

// When the user clicks on <span> (x), close the modal
span.onclick = function() {
    modal.style.display = "none";
}

// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
}}
</script>
     
         <div class="form">
         <form action="" method="post" class="niceform">
         
                <fieldset>
                    
                   
                    
                    
                     
                    
                </fieldset>
              
         </form>
         </div>  
      
     
     </div><!-- end of right content-->
            
                    
  </div>   <!--end of center content -->               
                    
                    
    
    
    <div class="clear"></div>
    </div> <!--end of main content-->
	
    
    
</div>		
</body>
</html>