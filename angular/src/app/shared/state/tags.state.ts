import { Action, Selector, State, StateContext } from '@ngxs/store';
import { produce } from 'immer';
import {Tag} from '@app/shared/model/tag.model';
import { Injectable } from '@angular/core';

export interface TagsModel {
  tags: Tag[];
}

export class SelectTagAction {
  static readonly type = '[Tags] select or unselect';
  constructor(public selectedTag: Tag) {}
}

function addOrRemoveTag(selectedTag): any {
  return produce(draft => {
    const index = draft.tags.findIndex(tag => tag.value === selectedTag.value);
    // console.log("index: " + index);
    if (index === -1) {
      draft.tags.push(selectedTag);
    } else {
      draft.tags.splice(index, 1);
    }
    console.log('draft: ' + JSON.stringify(draft));
  });
}

@State<TagsModel>({
  name: 'tags',
  defaults: {
    tags: []
  }
})
@Injectable()
export class TagsState {
  @Selector()
  static selectedTags(state: TagsModel): Tag[] {
    return state.tags;
  }

  @Action(SelectTagAction)
  selectTag(ctx: StateContext<TagsModel>, { selectedTag }: SelectTagAction): void {
    const state = ctx.getState();
    // console.log("state: " + JSON.stringify(state));
    ctx.setState(addOrRemoveTag(selectedTag));
  }
}
