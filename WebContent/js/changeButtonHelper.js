var newUnitTypeTemp = null;
var firstOneChosenForDefault = false;
	
function makeReload() {
	document.forms["AdminUnitForm"].submit();
}
	
function changeDocData(selectBox) {
	newUnitTypeTemp = selectBox.value;		
}
	
function chooseNewUnitType() {
	var dialog_buttons = {};
	dialog_buttons['OK'] = function() {
		$('#forSending').attr('value',newUnitTypeTemp);
		$(this).dialog('close');
		makeReload();
	}; 
	dialog_buttons['Loobu'] = function() {
		$(this).dialog('close');
	};
		
	$('#forUnitTypeChoosing').dialog({
		buttons: dialog_buttons,
		closeOnEscape: false,
		modal: true,
		open: function() {
			newUnitTypeTemp = 1; //to give it initial value in case dropbox index won't be changed
		}
	});
}