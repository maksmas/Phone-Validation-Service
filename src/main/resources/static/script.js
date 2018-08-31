const DEBOUNCE_MS = 500;
const VALIDATE_ENDPOINT = "/validate";

function validatePhone(phone) {
    if (phone && phone.length > 0) {
        let validationFlow = () => _sendValidateRequest(phone).then(
            validationResponse => _handleValidationResponse(validationResponse)
        );

        _debounce(validationFlow, DEBOUNCE_MS);
    } else {
        _reset();
    }
}

function _sendValidateRequest(phone) {
    return fetch(`${VALIDATE_ENDPOINT}?phone=${phone}`).then(
        wrappedResponse => wrappedResponse.json()
    );
}

function _handleValidationResponse(validationResponse) {
    if (validationResponse.valid) {
        _highlightInputAsValid();
        _displayCountrySection(validationResponse.countryCode);
    } else {
        _hideCountrySection();
        _highlightInputAsInvalid();
    }
}

function _hideCountrySection() {
    document.getElementById("countryWrapper").classList.add("hidden");
}

function _displayCountrySection(countryCode) {
    document.getElementById("countryWrapper").classList.remove("hidden");
    document.getElementById("country").innerText = countryCode;
    document.getElementById("country-flag").src = `./flags/${countryCode.toLowerCase()}.svg`;
}

function _highlightInputAsInvalid() {
    let classList = document.getElementById("phone-input").classList;

    classList.remove("valid");
    classList.add("invalid");
}

function _highlightInputAsValid() {
    let classList = document.getElementById("phone-input").classList;

    classList.remove("invalid");
    classList.add("valid");
}

function _reset() {
    document.getElementById("countryWrapper").classList.add("hidden");
    let classList = document.getElementById("phone-input").classList;

    classList.remove("invalid");
    classList.remove("valid");
}

let _debounceLock = null;
function _debounce(action, timeout) {
    clearTimeout(_debounceLock);
    _debounceLock = setTimeout(() => {
        _debounceLock = null;
        action();
    }, timeout);
}