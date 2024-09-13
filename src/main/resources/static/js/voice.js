const fromText = document.querySelector(".from-text"),
  toText = document.querySelector(".to-text"),
  exchangeIcon = document.querySelector(".exchange"),
  selectTag = document.querySelectorAll("select"),
  icons = document.querySelectorAll(".row i"),
  translateBtn = document.querySelector("#translateBtn");

let voices = [];
let speechSynthesis = window.speechSynthesis;

// Check available voices and log them
speechSynthesis.onvoiceschanged = function() {
  voices = speechSynthesis.getVoices();
  console.log("Available voices:", voices);
};

// Function to set voice based on language
function setVoice(lang, utterance) {
  let selectedVoice = voices.find(voice => voice.lang === lang);
  if (selectedVoice) {
    utterance.voice = selectedVoice;
  } else {
    console.warn(`No voice found for ${lang}`);
  }
}

// Fallback mechanism to English if the desired language voice is not available
function setVoiceWithFallback(lang, utterance) {
  let selectedVoice = voices.find(voice => voice.lang === lang) || voices.find(voice => voice.lang.startsWith('en'));
  if (selectedVoice) {
    utterance.voice = selectedVoice;
  }
}

selectTag.forEach((tag, id) => {
  for (let country_code in countries) {
    let selected = id === 0 ? (country_code === "en-GB" ? "selected" : "") : (country_code === "hi-IN" ? "selected" : "");
    let option = `<option ${selected} value="${country_code}">${countries[country_code]}</option>`;
    tag.insertAdjacentHTML("beforeend", option);
  }
});

exchangeIcon.addEventListener("click", () => {
  let tempText = fromText.value,
    tempLang = selectTag[0].value;
  fromText.value = toText.value;
  toText.value = tempText;
  selectTag[0].value = selectTag[1].value;
  selectTag[1].value = tempLang;
});

fromText.addEventListener("keyup", () => {
  if (!fromText.value) {
    toText.value = "";
  }
});

translateBtn.addEventListener("click", () => {
  translateText();
});

icons.forEach(icon => {
  icon.addEventListener("click", ({ target }) => {
    if (!fromText.value || !toText.value) return;

    if (target.classList.contains("fa-copy")) {
      if (target.id == "from") {
        navigator.clipboard.writeText(fromText.value);
      } else {
        navigator.clipboard.writeText(toText.value);
      }
    } else if (target.classList.contains("fa-volume-up")) {
      let textToSpeak = target.id === "from" ? fromText.value : toText.value;
      let langToSpeak = target.id === "from" ? selectTag[0].value : selectTag[1].value;
      let utterance = new SpeechSynthesisUtterance(textToSpeak);
      utterance.lang = langToSpeak;
      setVoiceWithFallback(langToSpeak, utterance);
      speechSynthesis.speak(utterance);
    }
  });
});

function startVoiceRecognition() {
  startConverting(function (recognizedText) {
    fromText.value = recognizedText;
  });
}

function startConverting() {
  if ('webkitSpeechRecognition' in window) {
    var speechRecognizer = new webkitSpeechRecognition();
    speechRecognizer.continuous = true;
    speechRecognizer.interimResults = true;
    speechRecognizer.lang = 'en-US';
    speechRecognizer.start();

    var finalTranscripts = '';

    speechRecognizer.onresult = function (event) {
      var interimTranscripts = '';
      for (var i = event.resultIndex; i < event.results.length; i++) {
        var transcript = event.results[i][0].transcript;
        transcript.replace("\n", "<br>");
        if (event.results[i].isFinal) {
          finalTranscripts += transcript;
        } else {
          interimTranscripts += transcript;
        }
      }

      fromText.value = finalTranscripts.trim();
      result.innerHTML = finalTranscripts + '<span style="color: #999">' + interimTranscripts + '</span>';
    };

    speechRecognizer.onerror = function (event) {
      console.error(event.error);
    };
  } else {
    result.innerHTML = 'Your browser is not supported. Please download Google Chrome or update your Google Chrome!';
  }
}

function translateText() {
  let text = fromText.value.trim(),
    translateFrom = selectTag[0].value,
    translateTo = selectTag[1].value;

  if (!text) return;

  toText.setAttribute("placeholder", "Translating...");

  let apiUrl = `https://api.mymemory.translated.net/get?q=${text}&langpair=${translateFrom}|${translateTo}`;

  fetch(apiUrl)
    .then(res => res.json())
    .then(data => {
      toText.value = data.responseData.translatedText;
      data.matches.forEach(data => {
        if (data.id === 0) {
          toText.value = data.translation;
        }
      });
      toText.setAttribute("placeholder", "Translation");
    });
}
