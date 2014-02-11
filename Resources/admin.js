function activatePopoversAndTooltips() {
	jQuery('.popo').each(function(index) {
		jQuery(this).popover({trigger:'hover',animation:'true', placement:'right'});
	});

	jQuery('.ttip').each(function(index) {
		jQuery(this).tooltip();
	});
}

jQuery(document).ready(function () {
	activatePopoversAndTooltips();
});