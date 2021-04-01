export interface IAccPp {
  id?: number;
  acc?: string | null;
}

export class AccPp implements IAccPp {
  constructor(public id?: number, public acc?: string | null) {}
}

export function getAccPpIdentifier(accPp: IAccPp): number | undefined {
  return accPp.id;
}
