<image-info> 

	<div id="image-block_{index}" class="image-block {getMaxDiff(imageInfo, opts.reference)}">

		<div class="image">
			<img src="{imageUrl}" data-id="{imageInfo['@id']}"></img>
		</div>
		
		<div class="info">
			<span class="image-label {getWidthDiff(imageInfo, opts.reference)}">Breite: {imageInfo.width}</span> 
			<span class="image-label {getHeightDiff(imageInfo, opts.reference)}">Höhe: {imageInfo.height}</span> 
		</div>
		
	</div>
	
<script>

this.imageInfo = {
		width : 0,
		height: 0
}

this.thumbnail = {
		width : 1000,
		height : 300
};


this.on("mount", function() {

   	this.imageInfo = this.opts.source;
    this.imageUrl = this.imageInfo["@id"] + "/full/!" + this.thumbnail.width + "," + this.thumbnail.height + "/0/default.jpg";
    this.update();
}.bind(this))

getMaxDiff(image1, image2) {
	
	let widthDiff = Math.abs(image1.width - image2.width);
	let heightDiff = Math.abs(image1.height - image2.height);
	let diff = Math.max(widthDiff, heightDiff);
	
	return this.getDiffClass(diff);
}

getWidthDiff(image1, image2) {
	let diff = Math.abs(image1.width - image2.width);
	return this.getDiffClass(diff);
}

getHeightDiff(image1, image2) {
	let diff = Math.abs(image1.height - image2.height);
	return this.getDiffClass(diff);
}

getDiffClass(diff) {
	let className;
	if(diff > 50) {
		className = "largeDiff";
	} else if(diff > 5) {
		className = "smallDiff";
	} else if(diff > 0) {
		className = "minimalDiff";
	} else {
		className = "exact";
	}
	return className;
}

</script> 

</image-info>