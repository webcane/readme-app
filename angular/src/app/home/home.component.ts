import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  hideBanner: boolean;

  constructor() {
    this.hideBanner = false;
  }

  ngOnInit(): void {
  }

  selectTags(tags) {
    console.log(tags);
  }
}
