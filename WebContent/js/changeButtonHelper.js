var newUnitTypeTemp = null;
	
function makeReload() {
	document.forms["AdminUnitForm"].submit();
}
	
function changeDocData(selectBox) {
	newUnitTypeTemp = selectBox.value;		
}
	
function chooseNewUnitType() {
	var dialog_buttons = {};
	dialog_buttons['OK'] = function() {
		$('#forSending').attr('value',newUnitTypeTemp); //here it is read by Spring
		$(this).dialog('close');
		makeReload();
	}; 
	dialog_buttons[$('#cancelButton').attr('value')] = function() {
		$(this).dialog('close');
	};
		
	$('#forUnitTypeChoosing').dialog({
		buttons: dialog_buttons,
		closeOnEscape: false,
		modal: true,
		open: function() {
			newUnitTypeTemp = 1; //initialization, 0 by default to evoke validation
		}
	});
}