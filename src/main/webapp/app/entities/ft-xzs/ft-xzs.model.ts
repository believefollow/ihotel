export interface IFtXzs {
  id?: number;
  roomn?: string | null;
}

export class FtXzs implements IFtXzs {
  constructor(public id?: number, public roomn?: string | null) {}
}

export function getFtXzsIdentifier(ftXzs: IFtXzs): number | undefined {
  return ftXzs.id;
}
