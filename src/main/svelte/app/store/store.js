import { writable } from 'svelte/store';

const state = {
  tags: writable([]),
};

const getTags = (tags) => {
  state.tags.update((old) => tags);
};

export { state, getTags };
