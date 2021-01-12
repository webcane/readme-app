import {Component, Input, OnInit} from '@angular/core';
import {Tag} from '@app/shared/model/tag.model';
import {Store} from '@ngxs/store';
import {SelectTagAction} from '@app/shared/state/tags.state';

@Component({
  selector: 'app-tag',
  templateUrl: './tag.component.html',
  styleUrls: ['./tag.component.scss']
})
export class TagComponent implements OnInit {

  @Input('tag') tag: Tag;

  active: string;

  constructor(private store: Store) { }

  ngOnInit(): void {
  }

  selectTag(tag: Tag) {
    this.active = (this.active ? '': 'tag-active');

    console.log("select tag: " + JSON.stringify(tag));
    this.store.dispatch(new SelectTagAction(tag));
  }

}
