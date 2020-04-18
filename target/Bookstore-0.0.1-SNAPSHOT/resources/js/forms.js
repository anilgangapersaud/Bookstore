function validatePassword(pass) {
	var password = pass.value;
	if (password.length < 8) {
		pass.style.boxShadow = " 0 0 5px rgba(255, 0, 0, 0.8)";
	} else {
		pass.style.boxShadow = " 0 0 5px rgba(81, 203, 238, 1)";
	}
}

function validateName(name) {
	var f = name.value;
	if (f.length === 0) {
		name.style.boxShadow = " 0 0 5px rgba(255, 0, 0, 0.8)";
	} else {
		name.style.boxShadow = " 0 0 5px rgba(81, 203, 238, 1)";
	}
}

function validateZip(zip) {
	
	var re = /^[A-Za-z][0-9][A-Za-z] [0-9][A-Za-z][0-9]$/;
	
	if (re.test(zip.value)) {
		zip.style.boxShadow = " 0 0 5px rgba(81, 203, 238, 1)";
	} else {
		zip.style.boxShadow = " 0 0 5px rgba(255, 0, 0, 0.8)";
	}
}

function validatePhone(phone) {
	var re = /^[0-9][0-9][0-9]-[0-9][0-9][0-9]-[0-9][0-9][0-9][0-9]$/;
	
	if (re.test(phone.value)) {
		phone.style.boxShadow = " 0 0 5px rgba(81, 203, 238, 1)";
	} else {
		phone.style.boxShadow = " 0 0 5px rgba(255, 0, 0, 0.8)";
	}
}

function validateCard(card) {
	
	if (card.value.length !== 16) {
		card.style.boxShadow = " 0 0 5px rgba(255, 0, 0, 0.8)";
	} else {
		card.style.boxShadow = " 0 0 5px rgba(81, 203, 238, 1)";
	}
}

function validateNumber(num) {
	var re = /^[0-1][0-9][0-9][0-9]$/;
	
	if (re.test(num.value)) {
		num.style.boxShadow = " 0 0 5px rgba(81, 203, 238, 1)";
	} else {
		num.style.boxShadow = " 0 0 5px rgba(255, 0, 0, 0.8)";
	}
}

function validateBid(bid) {
	var re = /^b[0-9][0-9][0-9]$/;
	
	if (re.test(bid.value)) {
		bid.style.boxShadow = " 0 0 5px rgba(81, 203, 238, 1)";
	} else {
		bid.style.boxShadow = " 0 0 5px rgba(255, 0, 0, 0.8)";
	}
}