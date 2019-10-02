import { Component, AfterViewInit } from "@angular/core";
import { EsService } from './es.service'

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

  constructor(private esService: EsService) {
  }

  ngAfterViewInit() {
    const elems = document.getElementsByClassName("slide") as HTMLCollectionOf<HTMLElement>;
  
    for (let i = 0; i < elems.length; i++) {
      elems.item(i).style.setProperty(
        "animation-delay",
        Math.round(Math.random() * 10) + "s"
      );
      elems.item(i).style.setProperty(
        "font-size",
        Math.round(Math.random() * 5 + 2) + "em"
      );
    }
  }

  onKeyUp(event) {
    this.esService.sayHi(event.target.value).subscribe((data) => {
      this.words.splice(0)

      for(let i = 0 ; i < data.length ; i++) {
        this.words.push(data[i])
      }
    })
  }

  r() {
    return Math.round(Math.random() * 100) % this.fake.length;
  }
}
