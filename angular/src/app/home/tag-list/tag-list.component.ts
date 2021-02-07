import {Component, OnInit} from '@angular/core';
import {TagsService} from '@app/shared/service/tags.service';
import {LoadingService} from '@app/shared/loading/loading.service';

@Component({
  selector: 'app-tag-list',
  templateUrl: './tag-list.component.html',
  styleUrls: ['./tag-list.component.scss']
})
export class TagListComponent implements OnInit {

  constructor(public tagsService: TagsService,
              public loadingService: LoadingService) {
  }

  ngOnInit(): void {
    this.tagsService.searchTags();
  }
}
