import * as store from './store';
import { parseItem, parseList } from './http-utils';
import {getContext} from "svelte";

import API from './config';
//const API = getContext("baseUrl");

export async function getTags() {
  console.log('load Tags');
  try {
    const response = await fetch(`${API}/tags`);
    const tags = await parseList(response);
    store.getTags(tags);

    return tags;
  } catch (error) {
    return console.log(error);
  }
}
