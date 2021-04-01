export interface IFtXz {
  id?: number;
  roomn?: string | null;
}

export class FtXz implements IFtXz {
  constructor(public id?: number, public roomn?: string | null) {}
}

export function getFtXzIdentifier(ftXz: IFtXz): number | undefined {
  return ftXz.id;
}
