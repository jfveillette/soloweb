function setvalue(i) {
	var objI = document.getElementById('i'+i);
	if (objI.checked) {
		objI.value = i;
		objI.name = 'registration_position';
	} else {
		objI.value = '';
		objI.name = '';
	}
}

function trySubmit(Sender) {
	var sMessage = '';
	var ele = null;
	var req = "";
	var uiname = "";
	var regexp = "";
	for (var i = 0 ; i < Sender.elements.length; i++) {
		ele = Sender.elements[i];
		req = "";
		if (objHasValue(ele.attributes["req"]))
			req = ele.attributes["req"].value;
		uiname = "";
		if (objHasValue(ele.attributes["uiname"]))
			uiname = ele.attributes["uiname"].value;
		if (((req == "true") || (ele.value.length > 0)) && (uiname > "")) { 
			if (uiname == 'ssn_is') { 
				if (!checkSSN(ele)) {
					sMessage = sMessage + 'Kennitala ekki rétt!\n'; 
				}
			} else {
				regexp = "";
				if (objHasValue(ele.attributes['regexp']))
					regexp = ele.attributes['regexp'].value;
				if (regexp > "") {
					if (!validateInput(ele)) {
						switch (uiname) {
							case 'password': 
								sMessage = sMessage + 'Lykilorð uppfyllir ekki skilyrði!\n';
								break;
							case 'email':
								sMessage = sMessage + 'Netfang ekki rétt!\n';
								break;
							case 'firstname':
								sMessage = sMessage + 'Nafn vantar!\n';
								break;
							case 'cardexpirydate':
								sMessage = sMessage + 'Gildistími er ekki réttur!\n';
								break;
							default:
								sMessage = sMessage + ele.attributes['uiname'].value + ' er ekki rétt!\n'; 
								break;
						}
					}
					if (uiname == 'password') {
						if (ele.value != Sender.password2.value) {
							sMessage = sMessage + 'Lykilorð ekki eins!\n';
						}
					}
				}
			}
		}
	}
	if (sMessage == '') {
		return true;
	} else {
		alert(sMessage);
		//return true;
		return false;
	}
}

function validateInput(oInp) {
	var regExp = new RegExp(oInp.attributes['regexp'].value, 'mig');
	if (regExp.test(oInp.value)) {
		return true;
	} else {
		return false;
	}
}

function checkSSN(oInp) {
	var sSSN = oInp.value;
	sSSN = sSSN .replace(/-/,'')
	if ((sSSN.length < 10) || (sSSN.length > 10)) 
	{
		return false;
	}
	var iLast = sSSN.slice((sSSN.length - 1))
	if (iLast != 8 && iLast != 9 && iLast != 10) 
	{
		return false;
	}
	re = /\D+/;
	var isNum = re.test(sSSN);
	if (isNum == true)
	{
		return false;
	}
	var arrDig1 = new Array(10)
	var arrDig2 = new Array(8)
	var iSum = 0;
	for (var i = 0; i < 10; i++) {
		arrDig1[i] = sSSN.slice(i,i+1);
	}
	arrDig2[0] = arrDig1[0] * 3;
	arrDig2[1] = arrDig1[1] * 2;
	arrDig2[2] = arrDig1[2] * 7;
	arrDig2[3] = arrDig1[3] * 6;
	arrDig2[4] = arrDig1[4] * 5;
	arrDig2[5] = arrDig1[5] * 4;
	arrDig2[6] = arrDig1[6] * 3;
	arrDig2[7] = arrDig1[7] * 2;
	for (var i = 0; i < 8; i++) {
		var iDig = parseInt(arrDig2[i]);
		iSum = iSum + iDig;
	}
	var iLeft = iSum % 11;
	var iVart = 11 - iLeft;
	if ((iVart == 11) || (iVart == 10)) {
		iVart = 0;
	}
	if (iVart != arrDig1[8]) {
		return false;
	}
	return true;
}

function objHasValue(obj) {
	return (typeof(obj) != "untitled" && obj != null);
}

function strHasValue(str) {
	return objHasValue(str) && str > "";
}
