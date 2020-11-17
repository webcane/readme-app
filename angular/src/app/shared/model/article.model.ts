import {Tag} from '@app/shared/model/tag.model';

export interface Article {
  url: string;
  title: string;
  preamble: string;
  tags: Tag[];
}
