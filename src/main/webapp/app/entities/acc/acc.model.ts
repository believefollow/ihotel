export interface IAcc {
  id?: number;
  acc?: string | null;
}

export class Acc implements IAcc {
  constructor(public id?: number, public acc?: string | null) {}
}

export function getAccIdentifier(acc: IAcc): number | undefined {
  return acc.id;
}
