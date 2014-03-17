$(document).ready(function() {
	/* $(".newtable tbody tr:even").addClass("neweven"); */
	$(".newtable").addClass("newtable");
	$(".newtable thead tr").addClass("newthead");
	$(".newtable tbody tr:even").addClass("neweven");
	$(".newtable tbody tr:odd").addClass("newodd");
});

function initradio(rName, rValue) {
	var rObj = document.getElementsByName(rName);

	for (var i = 0; i < rObj.length; i++) {
		if (rObj[i].value == rValue) {
			rObj[i].checked = 'checked';
		}
	}
}