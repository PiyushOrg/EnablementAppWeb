<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Kony EVENTS | ADMIN PANEL</title>
<link rel="stylesheet" type="text/css" href="style.css" />
<script type="text/javascript" src="JS/jquery.min.js"></script>
<script type="text/javascript" src="JS/ddaccordion.js"></script>
<script type="text/javascript">
ddaccordion.init({
	headerclass: "submenuheader", //Shared CSS class name of headers group
	contentclass: "submenu", //Shared CSS class name of contents group
	revealtype: "click", //Reveal content when user clicks or onmouseover the header? Valid value: "click", "clickgo", or "mouseover"
	mouseoverdelay: 200, //if revealtype="mouseover", set delay in milliseconds before header expands onMouseover
	collapseprev: true, //Collapse previous content (so only one open at any time)? true/false 
	defaultexpanded: [], //index of content(s) open by default [index1, index2, etc] [] denotes no content
	onemustopen: false, //Specify whether at least one header should be open always (so never all headers closed)
	animatedefault: false, //Should contents open by default be animated into view?
	persiststate: true, //persist state of opened contents within browser session?
	toggleclass: ["", ""], //Two CSS classes to be applied to the header when it's collapsed and expanded, respectively ["class1", "class2"]
	togglehtml: ["suffix", "<img src='images/plus.gif' class='statusicon' />", "<img src='images/minus.gif' class='statusicon' />"], //Additional HTML added to the header when it's collapsed and expanded, respectively  ["position", "html1", "html2"] (see docs)
	animatespeed: "fast", //speed of animation: integer in milliseconds (ie: 200), or keywords "fast", "normal", or "slow"
	oninit:function(headers, expandedindices){ //custom code to run when headers have initalized
		//do nothing
	},
	onopenclose:function(header, index, state, isuseractivated){ //custom code to run whenever a header is opened or closed
		//do nothing
	}
})
</script>
<script src="jquery.jclock-1.2.0.js.txt" type="text/javascript"></script>
<script type="text/javascript" src="JS/jconfirmaction.jquery.js"></script>
<script type="text/javascript">
	
	$(document).ready(function() {
		$('.ask').jConfirmAction();
	});
	
</script>


<script language="javascript" type="text/javascript" src="JS/niceforms.js"></script>
<link rel="stylesheet" type="text/css" media="all" href="niceforms-default.css" />

</head>
<body>
<div id="main_container">

	<div class="header">
    <div class="logo"><a href="#"><img src="images/ko.png" alt="" title="" border="0" /></a></div>
    
    <div class="right_header">Welcome Admin,  |  <a href="#" class="logout">Logout</a></div>
    <div class="jclock"></div>
    </div>
    
    <div class="main_content">
    
                    <div class="menu">
                    <ul>
                    <li><a class="current" href="index.html">Admin Home</a></li>
                    <li><a href="list.html">EVENTS<!--[if IE 7]><!--></a><!--<![endif]-->
                    <!--[if lte IE 6]><table><tr><td><![endif]-->
                        <ul>
                        <li><a href="" title="">CREATE EVENT</a></li>
                        <li><a href="" title="">EDIT EVENT</a></li>
						<li><a href="" title="">ONGOING EVENTS</a></li>
                        <li><a href="" title="">HISTORY</a></li>
                        
                        </ul>
                    <!--[if lte IE 6]></td></tr></table></a><![endif]-->
                    </li>
                    
                    </div>            
    
               <div class="center_content">  
    
    
    
    <div class="left_content">
    
    		<div class="sidebar_search">
            <form>
            <input type="text" name="" class="search_input" value="search keyword" onclick="this.value=''" />
            <input type="image" class="search_submit" src="images/search.png" />
            </form>            
            </div>           
                          
    </div>  
        <div class="right_content">            
                            <h2>Create New Event</h2>
              <div class="form">
         <form action="" method="post" class="niceform" id="myform">
                         <fieldset>                                      
                    <dl>
					<dt><label for ="name"> Name of the Event </label></dt>
                    <dd><input type="text" name="text" id="name"></dd>
					<dt><label for ="Description"> Description </label></dt>
                    <dd><input type="text" name="text" id="description"></dd>
                    
                        <dt><label for="upload">Upload a File:</label></dt>
                        <dd><input type="file" name="upload" id="upload" /></dd>						
						<label for="Stages"><h2>Add/Remove Stages</h2></label><br>
	<script type="text/javascript">
$(document).ready(function(){
    var counter = 1;
    $("#addButton").click(function () {
	if(counter>10){
            alert("Only 10 textboxes allow");
            return false;
	}
	var newTextBoxDiv = $(document.createElement('div'))
	     .attr("id", 'TextBoxDiv' + counter);
	newTextBoxDiv.after().html('<h5><label>Stage No#'+ counter + ' : </label>' +
	      '<input type="textbox" class="form" name="text' + counter +
	      '" id="textbox' + counter + '" value="" ><dl><label> Description :</label><input type="text" class="form"  name="desc'+ counter +'"><dl><label>Start Date&Time</label><input type="datetime-local" class="form"  value="2016-01-01T00:00:00" /><dl><label>End Date&Time</label><input type="datetime-local" class="form"  value="2016-01-01T00:00:00" />');
	newTextBoxDiv.appendTo("#TextBoxesGroup");
	counter++;
     });
     $("#removeButton").click(function () {
	if(counter==1){
          alert("There should be atleast one stage");
          return false;
       }
	counter--;
        $("#TextBoxDiv" + counter).remove();
     });     
  });
</script>
<div id='TextBoxesGroup'>
	<div id="TextBoxDiv1">
	</div>
</div>
<input type='button' value='Add Stage' id='addButton'>
<input type='button' value='Remove Stage' id='removeButton'> 
                     <dl><dt><dt><input type='button' value='SUBMIT' id='SUBMIT'> </dt></dt></dl>
                    
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