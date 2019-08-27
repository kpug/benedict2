import { Component } from "@angular/core";

@Component({
  selector: "app-home",
  templateUrl: "./home.component.html",
  styleUrls: ["./home.component.scss"]
})
export class HomeComponent {
  words: String[] = [];

  fake: String[] = [
    "ask",
    "be",
    "become",
    "begin",
    "call",
    "can",
    "come",
    "could",
    "do",
    "feel",
    "find",
    "get",
    "give",
    "go",
    "have",
    "hear",
    "help",
    "keep",
    "know",
    "leave",
    "let",
    "like",
    "live",
    "look",
    "make",
    "may",
    "mean",
    "might",
    "move",
    "need",
    "play",
    "put",
    "run",
    "say",
    "see",
    "seem",
    "should",
    "show",
    "start",
    "take",
    "talk",
    "tell",
    "think",
    "try",
    "turn",
    "use",
    "want",
    "will",
    "work",
    "would"
  ];

  onKeyUp(event) {
    // console.log(event.target.value);
    if (event.target.value === "") {
      this.words.splice(0);
    } else {
      this.words = [
        this.fake[this.r()],
        this.fake[this.r()],
        this.fake[this.r()],
        this.fake[this.r()],
        this.fake[this.r()]
      ];
    }
  }

  r() {
    return Math.round(Math.random() * 100) % this.fake.length;
  }
}
