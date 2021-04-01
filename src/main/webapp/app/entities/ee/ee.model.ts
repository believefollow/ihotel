export interface IEe {
  id?: number;
  acc?: string | null;
}

export class Ee implements IEe {
  constructor(public id?: number, public acc?: string | null) {}
}

export function getEeIdentifier(ee: IEe): number | undefined {
  return ee.id;
}
