$(document).ready(function(){
	var $distressedSurfacesImages = $('.dialog-distressed-surfaces-image');
	
	var setDistressedSurfaces = function(image, surfaceIndex) {
		var imageClassName = image.className;
		var i = imageClassName.lastIndexOf('tooth_index_');
		var toothIndex = imageClassName.substring(i + 12, imageClassName.lenght);
		
		var src = image.src;
		i = src.lastIndexOf('/');
		var j = src.indexOf('.svg.xhtml');
		var srcBefore =  src.substring(0, i + 1);
		var srcAfter =  src.substring(j, src.lenght);
		var srcDistressedSurfaces =  src.substring(i+1, j);
		
		var items = srcDistressedSurfaces.split('_');
		
		var index = items.indexOf(surfaceIndex);
		var defaultIndex = items.indexOf('default');
		
		if (index > -1) {
			items.splice(index, 1);
			console.log(items.lenght);
			if (items.length == 0) {
				items.push('default');	
			}
		} else if (defaultIndex > -1){
			items.splice(index, 1);
			items.push(surfaceIndex);
		} else {
			items.push(surfaceIndex);
		}
		items.sort();
		
		var itemsStr = items.join("_");
		console.log(".distressed_surfaces_" + toothIndex);
		$("input.distressed_surfaces_" + toothIndex).each(function(){
			$(this).val(itemsStr)
		});
		
		image.src = srcBefore + itemsStr + srcAfter;
	}
	
	$distressedSurfacesImages.click(function(event) {
		var src = event.target.src;
		var i = src.lastIndexOf('/');
		var srcDistressedSurfacesNumber = src.substring(i - 1, i);
		
		var x1= event.offsetX;
		var centerX= 50;
		
		var y1= event.offsetY;
		var centerY= 40;
		
		var r = 30;
		
		var isInBigCircle = Math.sqrt((x1-centerX)*(x1-centerX) + (y1-centerY)*(y1-centerY)) < r;
		
		r = 14;
		var isInLitleCircle = Math.sqrt((x1-centerX)*(x1-centerX) + (y1-centerY)*(y1-centerY)) < r;
		
		if (srcDistressedSurfacesNumber == 5 && isInLitleCircle) {
			setDistressedSurfaces(event.target, '5');
		} else if (isInBigCircle) {
			var x = x1 - centerX;
			var y = centerY - y1;
			var angle = 0;
			if (x >= 0 && y >= 0) {
				angle = Math.atan(y/x) * 180/Math.PI;
			} else if (x <= 0 && y >= 0) {
				angle = Math.atan(Math.abs(x)/y) * 180/Math.PI + 90; 
			} else if (x <= 0 && y <= 0){
				angle = Math.atan(Math.abs(y)/Math.abs(x)) * 180/Math.PI + 180;
			} else if (x >= 0 && y <= 0){
				angle = Math.atan(x/Math.abs(y)) * 180/Math.PI + 270;
			}
			
			
			if (angle > 315 && angle <= 360 || angle >= 0 && angle < 45) {
				setDistressedSurfaces(event.target, '2');
			} else if (angle >= 45 && angle < 135) {
				setDistressedSurfaces(event.target, '1');
			} else if (angle >= 135 && angle < 225) {
				setDistressedSurfaces(event.target, '4');
			} else if (angle >= 225 && angle < 315) {
				setDistressedSurfaces(event.target, '3');
			}
		}
	});
	
});