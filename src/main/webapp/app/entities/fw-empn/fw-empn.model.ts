export interface IFwEmpn {
  id?: number;
  empnid?: string;
  empn?: string;
  deptid?: number | null;
  phone?: string | null;
}

export class FwEmpn implements IFwEmpn {
  constructor(
    public id?: number,
    public empnid?: string,
    public empn?: string,
    public deptid?: number | null,
    public phone?: string | null
  ) {}
}

export function getFwEmpnIdentifier(fwEmpn: IFwEmpn): number | undefined {
  return fwEmpn.id;
}
