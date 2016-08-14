function checkPermission(wantPermission) {
	var temp = [];
	var permissions = sessionStorage.permissions.split(",");
	for (var i = 0; i < permissions.length; i++) {
		var one = permissions[i];
		if (wantPermission.indexOf(one)!=-1) {
			temp.push(true);
		}
	}
	if (temp.length == wantPermission.length||sessionStorage.role=="超级管理员") {
		return true;
	}
	else {
		return false;
	}
}