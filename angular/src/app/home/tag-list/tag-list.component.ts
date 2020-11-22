import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {TagsService} from '@app/shared/service/tags.service';
import {Observable} from 'rxjs';
import {Tag} from '@app/shared/model/tag.model';

@Component({
  selector: 'app-tag-list',
  templateUrl: './tag-list.component.html',
  styleUrls: ['./tag-list.component.css']
})
export class TagListComponent implements OnInit {

  tags$: Observable<Tag[]>;
  loading$: Observable<boolean>;

  // public selectedTags;
  @Output("selectedTags")
  selectedTags: EventEmitter<any> = new EventEmitter();

  tagSet = new Set();

  constructor(public tagsService: TagsService) {
    this.tags$ = tagsService.tags$;
    this.loading$ = tagsService.loading$;
  }

  ngOnInit(): void {
  }

  // TODO
  selectTag(tag: Tag) {
    console.log("filter: " + JSON.stringify(tag));
    if(this.tagSet.has(tag)) {
      this.tagSet.delete(tag);
    } else {
      this.tagSet.add(tag);
    }
    this.selectedTags.emit(this.tagSet);
  }

  // setListTo(type: string = '', filters: Object = {}) {
  //   // If feed is requested but user is not authenticated, redirect to login
  //   if (type === 'feed' && !this.isAuthenticated) {
  //     this.router.navigateByUrl('/login');
  //     return;
  //   }
  //
  //   // Otherwise, set the list object
  //   this.listConfig = {type: type, filters: filters};
  // }

}
