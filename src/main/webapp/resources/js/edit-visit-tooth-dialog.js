$(document).ready(function(){
	var $distressedSurfacesImages = $('.distressed-surfaces-image');
	
	$distressedSurfacesImages.click(function(event) {
	
		var x1= event.offsetX;
		var centerX= 50;
		
		var y1= event.offsetY;
		var centerY= 40;
		
		var r = 30;
		
		var isInBigCircle = Math.sqrt((x1-centerX)*(x1-centerX) + (y1-centerY)*(y1-centerY)) < r;
		
		if (isInBigCircle) {
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
			
			
			if (angle > 225 && angle <= 360 || angle >= 0 && angle < 45) {
				//
				console.log();
				
				var src = event.target.src;
				var i = src.lastIndexOf('/');
				var j = src.indexOf('.svg.xhtml');
				var srcBefore =  src.substring(0, i + 1);
				var srcAfter =  src.substring(j, src.lenght);
				var srcDistressedSurfaces =  src.substring(i+1, j);
				
				console.log(srcBefore);
				console.log(srcAfter);
				console.log(srcDistressedSurfaces);
				
				var items = srcDistressedSurfaces.split('_');
				
				var index = items.indexOf('2');
				if (index > -1) {
					items.splice(index, 1);
				} else {
					items.push('2');
				}
				items.sort();
				
				console.log(items.join("_"));
				event.target.src = srcBefore + items.join("_") + srcAfter;
			}
			
		}
		
		
	});
	
});