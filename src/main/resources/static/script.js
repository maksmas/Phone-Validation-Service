const DEBOUNCE_MS = 300;
const VALIDATE_ENDPOINT = "/validate";
const ENCODED_PLUS = encodeURIComponent("+");

function validatePhone(phone) {
    if (phone && phone.length > 1) {
        if (phone[0] === "+") {
            let validationFlow = () => _sendValidateRequest(phone).then(
                validationResponse => _handleValidationResponse(validationResponse)
            );

            _debounce(validationFlow, DEBOUNCE_MS);
        } else {
            _highlightInputAsInvalid();
            _displayError("Number should start with +");
        }
    } else {
        _reset();
    }
}

function _sendValidateRequest(phone) {
    return fetch(`${VALIDATE_ENDPOINT}?phone=${ENCODED_PLUS}${phone.substring(1)}`).then(
        wrappedResponse => wrappedResponse.json()
    );
}

function _handleValidationResponse(validationResponse) {
    if (validationResponse.valid) {
        _highlightInputAsValid();
        _displayCountrySection(validationResponse.countries);
    } else {
        _highlightInputAsInvalid();
        _displayError(validationResponse.errorMessage);
    }
}

function _clearCountrySection() {
    const countryWrapper = document.getElementById("countryWrapper");

    while (countryWrapper.firstChild) {
        countryWrapper.removeChild(countryWrapper.firstChild);
    }
}

function _displayCountrySection(countries) {
    _clearCountrySection();

    const wrapperElement = document.getElementById("countryWrapper");

    for (const country of countries) {
        let imgElement = document.createElement("img");
        imgElement.setAttribute("src", `./flags/${country.code}.svg`);
        imgElement.setAttribute("onerror", "this.src = './flags/globe.svg'");

        let spanElement = document.createElement("span");
        spanElement.appendChild(document.createTextNode(`${country.title} (${country.code})`));

        wrapperElement.appendChild(imgElement);
        wrapperElement.appendChild(spanElement);
        wrapperElement.appendChild(document.createElement("br"));
    }
}

function _displayError(error) {
    _clearCountrySection();

    const wrapperElement = document.getElementById("countryWrapper");

    let spanElement = document.createElement("span");
    spanElement.classList.add("error");
    spanElement.appendChild(document.createTextNode(error));

    wrapperElement.appendChild(spanElement);
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
    _clearCountrySection();
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