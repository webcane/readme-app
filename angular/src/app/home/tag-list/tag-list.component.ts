import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {TagsService} from '@app/shared/service/tags.service';
import {Observable} from 'rxjs';
import {Tag} from '@app/shared/model/tag.model';
import {Store} from '@ngxs/store';
import {SelectTagAction} from '@app/shared/state/tags.state';

@Component({
  selector: 'app-tag-list',
  templateUrl: './tag-list.component.html',
  styleUrls: ['./tag-list.component.css']
})
export class TagListComponent implements OnInit {

  tags$: Observable<Tag[]>;
  loading$: Observable<boolean>;

  constructor(public tagsService: TagsService,
              private store: Store) {
    this.tags$ = tagsService.tags$;
    this.loading$ = tagsService.loading$;
  }

  ngOnInit(): void {
  }

  selectTag(tag: Tag) {
    console.log("select tag: " + JSON.stringify(tag));
    this.store.dispatch(new SelectTagAction(tag));
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
