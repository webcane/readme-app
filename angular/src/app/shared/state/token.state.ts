import { Injectable } from '@angular/core';
import { Action, Selector, State, StateContext } from '@ngxs/store';
import { produce } from 'immer';

export interface TokenModel {
  token: string;
}

export class TokenSetAction {
  static readonly type = '[token] set token';
  constructor(public token: string) {}
}

export class TokenCancelAction {
  static readonly type = '[token] cancel token';
}
@State<TokenModel>({
  name: 'token',
  defaults: {
    token: undefined
  }
})
@Injectable()
export class TokenState {

  @Selector()
  static token(state: TokenModel): string {
    return state.token;
  }

  @Action(TokenSetAction)
  setToken(ctx: StateContext<TokenModel>, { token }: TokenSetAction): void {
    ctx.setState(produce(draft => {
       draft.token = token;
    }));
  }

  @Action(TokenCancelAction)
  cancelToken(ctx: StateContext<TokenModel>): void {
    ctx.setState(produce(draft => {
       draft.token = undefined;
    }));
  }
}
