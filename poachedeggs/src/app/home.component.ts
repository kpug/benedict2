import { Component, AfterViewInit } from "@angular/core";
import { EsService } from "./es.service";

@Component({
  selector: "app-home",
  templateUrl: "./home.component.html",
  styleUrls: ["./home.component.scss"]
})
export class HomeComponent implements AfterViewInit {
  constructor(private esService: EsService) {}
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
    const elems = document.getElementsByClassName("slide");
    for (let i = 0; i < elems.length; i++) {
      elems
        .item(i)
        .setAttribute(
          "style",
          `animation-delay: ${Math.round(
            Math.random() * 20
          )}s; font-size: ${Math.round(Math.random() * 5 + 2)}em`
        );
    }
  }

  onKeyUp(event) {
    if (event.target.value === "") {
      this.words.splice(0);
    } else {
      this.esService.sayHi(event.target.value).subscribe(data => {
        this.words.splice(0);

        for (let d of data) {
          this.words.push(d);
        }
      });
    }
  }

  r() {
    return Math.round(Math.random() * 100) % this.fake.length;
  }
}
