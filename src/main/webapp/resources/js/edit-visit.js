$(document).ready(function(){
	var $copyPriceToPaidButton = $('.copy-price-to-paid-button');
	var $formGroup = $copyPriceToPaidButton.closest('.form-group');
	
	$copyPriceToPaidButton.click(function(){
		var price = $formGroup.find('.price-input').val();
		$formGroup.find('.paid-input').val(price);
	});
	
});