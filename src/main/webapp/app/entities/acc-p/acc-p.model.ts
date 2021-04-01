export interface IAccP {
  id?: number;
  acc?: string;
}

export class AccP implements IAccP {
  constructor(public id?: number, public acc?: string) {}
}

export function getAccPIdentifier(accP: IAccP): number | undefined {
  return accP.id;
}
