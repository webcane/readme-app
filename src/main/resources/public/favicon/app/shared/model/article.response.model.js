import {Article} from "./article.model";

export interface ArticleResponse {
  total: number;
  articles: Article[];
}
