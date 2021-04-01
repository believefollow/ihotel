export interface IAccbillno {
  id?: number;
  account?: string;
  accbillno?: string | null;
}

export class Accbillno implements IAccbillno {
  constructor(public id?: number, public account?: string, public accbillno?: string | null) {}
}

export function getAccbillnoIdentifier(accbillno: IAccbillno): number | undefined {
  return accbillno.id;
}
