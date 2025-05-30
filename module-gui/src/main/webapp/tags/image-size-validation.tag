<image-size-validation>
	<link rel="stylesheet" href="/goobi/plugins/{plugin_name}/css/style.css">
	<div class="box box-color lightgrey box-bordered">
         <div class="box-title">
             <h3>
                 <i class="fa fa-puzzle-piece"></i>
                 Bildgrößen validieren
             </h3>
             <div class="actions">
				<a class="btn btn-mini btn-default" onclick={leave}>Plugin verlassen</a>
             </div>
         </div>

         <div class="box-content" style="background-color:#eee">
         		<div class="content-left">
	         		<image-info each="{info, index in imageList}"  index="{index}" source="{info}" reference="{this.reference}" onclick="{selectImage}"></image-info>
         		</div>
		</div>
		<div  class="large-image" onclick="{hideLargeImage}">
			<div class="container">
				<img src="{selectedImage}"></img>
			</div>
		</div>
		<div class="controls">
			<div class="controls-item" data-hide="smallDiff,minimalDiff,exact" onclick="{hide}">
				<div class="controls-item-icon">
					<div class="largeDiff"></div>
				</div>
				<div class="controls-item-label">
					<label> &gt; 50px Differenz</label>
				</div>
			</div>
			<div class="controls-item" data-hide="minimalDiff,exact" onclick="{hide}">
				<div class="controls-item-icon">
					<div class="smallDiff"></div>
				</div>
				<div class="controls-item-label">
					<label> &gt; 5px Differenz</label>
				</div>
			</div>
			<div class="controls-item" data-hide="exact" onclick="{hide}">
				<div class="controls-item-icon">
					<div class="minimalDiff"></div>
				</div>
				<div class="controls-item-label">
					<label> &gt; 0px Differenz</label>
				</div>
			</div>
			<div class="controls-item">
			Klick auf einen der obigen spalten: Alle Bilder mit kleinerer Differenz als der angeklickten werden verborgen. Nochmaliges Klicken hebt die Auswahl wieder auf.
			</div>
			<div class="controls-item">
			Klick auf ein Bild: Bild groß darstellen. Das große Bild verschwindet nach erneutem Klick.
			</div>
			<div class="controls-item">
			Shift + Klick auf ein Bild: Bild als Größenreferenz verwenden. All Bilder werden mit diesem Bild verglichen.
			</div>
		</div>
	</div>
	
	<script>
		console.log("start javscript ", this);
		this.plugin_name = window["plugin_name"];
	    this.generalOpts = window[window["plugin_name"]]; 
	    this.showAll = true;
	    this.selectedImage = undefined;
		const goobi_path = location.pathname.split('/')[1];
// 	    this.imageListUrl = "/goobi/api/image/list/" + this.generalOpts.processId + "/media";
		this.imageListUrl = "/${goobi_path}/api/plugins/isv/process/"+ this.generalOpts.processId +"/media/list";
		console.log("call url ", this.imageListUrl);
	    
		this.on("mount", function() {
		    console.log("mounting image-size-validation", this.imageListUrl)
		    Q($.ajax( {
		        url: this.imageListUrl,
		        type: "GET",
		        datatype: "JSON"
		    }))
		    .then(function(list) {
		    	let promises = list.map(function(url) {
		    		return Q($.ajax( {
				        url: url,
				        type: "GET",
				        datatype: "JSON"
				    }))
		    	});
		    	return Q.all(promises);
		    })
		    .then(function(list) {
		    	this.imageList = list; 
		    	this.reference = this.imageList[0];
		    	this.update();
		    }.bind(this));
		}.bind(this))
		
		this.on("unmount", () => {
		    if(this.clickListener) {
		        document.removeEventListener('click', this.clickListener);
		    }
		    if(this.keydownListener) {
		        document.removeEventListener('keydown', this.keydownListener);
		    }
		})

		selectImage(e) {
		    console.log("click ", e);
			let $image =$(e.target);
			let id = $image.attr("data-id");
			if(id) {		    	
		    	if(e.shiftKey) {	
		    		this.showAll();
				    let index = this.imageList.findIndex( (info) => info['@id'] === id );
				    if(index > -1) {		    	
// 					    console.log("Found image ", this.imageList[index], " at ", index);
					    this.reference = this.imageList[index];
					    this.update();
				    } else {
				    	console.error("Could not find image ", id);
				    }
			    } else {
			    	this.selectedImage = id + "/full/max/0/default.jpg";
			    	$(".large-image").show();
			    	this.update();
			    }
		    }
		}
		
		hideLargeImage() {
			this.selectedImage = undefined;
	    	$(".large-image").hide();
	    	this.update();
		}
				
		compare(size1, size2) {
			let diff = Math.abs(size1, size2);
			
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
		
		showAll() {
			$(".image-block").show();
			$(".controls-item").removeClass("selected");
			this.hidden = undefined;
		}
		
		hide(e) {
			console.log("hide ", e);
			let sizes = $(e.target).closest(".controls-item").data("hide");
			if(sizes !== this.hidden) {
				this.showAll();
				this.hidden = sizes;
				sizes = sizes.split(",");
				$(e.target).closest(".controls-item").addClass("selected")
				sizes.forEach(function(size) {
					let selector = ".image-block." + size;
					$(selector).hide();				
				})
			} else {
				this.showAll();
			}
		}
		
		save() {
		    $.ajax( {
		        url: "/${goobi_path}/api/plugins/{plugin_name}/process/" + this.generalOpts.processId + "/save",
		        type: "POST",
		        contentType: "application/json; charset=utf-8",
		        data: JSON.stringify(this.ddHistory[this.ddIdx])
		    }).then(function(data) {
		        console.log(data)
		        alert("gespeichert")
		    }.bind(this))
		}
		
		
		leave() {
				document.getElementById("restPluginFinishLink").click();
		}
	</script>
</image-size-validation>
