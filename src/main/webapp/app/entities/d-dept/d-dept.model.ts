export interface IDDept {
  id?: number;
  deptid?: number;
  deptname?: string;
}

export class DDept implements IDDept {
  constructor(public id?: number, public deptid?: number, public deptname?: string) {}
}

export function getDDeptIdentifier(dDept: IDDept): number | undefined {
  return dDept.id;
}
