/* hide and show the reviews buttons from book_details */
function activeReviews( elementId,buttonId, type) {
  var element = document.getElementById(elementId);
  var button = document.getElementById(buttonId);
 
  if (element.style.display === "none") {
	element.style.display = "block";
	if (type == 1)
		button.innerHTML="Don't add a review";
	else
		button.innerHTML = "Hide reviews";
  } else {
	element.style.display = "none";
	if (type == 1)
		button.innerHTML="Add a review";
	else
		button.innerHTML = "Show reviews";
	
  }
}
/* Validates the length of the reviewer's name which is at most 50 and 
the length of the review which is at most 255.
*/ 
function validate() 
{
	var ok = true;
	var p = document.getElementById("reviewer").value;
	 var error = document.getElementById("errorMessage");
	if (p.length > 50) 
	{
		error.style.display ="inline";
		/*document.getElementById("principal-error").style.display ="inline";
		document.getElementById("principal-error").style.color = "red"; */
		ok = false;
	} 
	else 
	{
		//document.getElementById("principal-error").style.display = "none"; 
	}
	if (!ok)
		return false;
	p = document.getElementById("review").value; 
	if (p.length > 255)
	{
		error.style.display ="inline"; 
		ok = false; 
	} 
	
	if (!ok)
		return false;
	error.style.display ="none";
	return ok;

}