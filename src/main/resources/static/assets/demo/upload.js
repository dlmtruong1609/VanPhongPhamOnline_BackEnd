document.getElementById("files").onchange = function () {
    var reader = new FileReader();

    reader.onload = function (e) {
        // get loaded data and render thumbnail.
        document.getElementById("image").src = e.target.result;
    };

    // read the image file as a data URL.
    reader.readAsDataURL(this.files[0]);

};

for (let i = 0; i < document.getElementsByClassName("fileUpload").length; i++) {
	document.getElementById("files" + i).onchange = function () {
	    let reader = new FileReader();
	    let index = i;
	    reader.onload = function (e) {
	        // get loaded data and render thumbnail.
	        document.getElementById("image" + index).src = e.target.result;
	    };
	    // read the image file as a data URL.
	    reader.readAsDataURL(this.files[0]);
	};	
}