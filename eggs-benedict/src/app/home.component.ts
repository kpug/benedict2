import { Component, AfterViewInit } from "@angular/core";
import { StaticSymbolResolver, ElementSchemaRegistry } from "@angular/compiler";

@Component({
  selector: "app-home",
  templateUrl: "./home.component.html",
  styleUrls: ["./home.component.scss"]
})
export class HomeComponent implements AfterViewInit {
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

  ngAfterViewInit() {
    console.log(this.fake);
    const elems = document.getElementsByClassName("slide");

    for (let i = 0; i < elems.length; i++) {
      elems[i].style.setProperty(
        "animation-delay",
        Math.round(Math.random() * 10) + "s"
      );
      elems[i].style.setProperty(
        "font-size",
        Math.round(Math.random() * 5 + 2) + "em"
      );
    }
  }

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
