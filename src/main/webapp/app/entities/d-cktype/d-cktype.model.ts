export interface IDCktype {
  id?: number;
  cktype?: string;
  memo?: string | null;
  sign?: string;
}

export class DCktype implements IDCktype {
  constructor(public id?: number, public cktype?: string, public memo?: string | null, public sign?: string) {}
}

export function getDCktypeIdentifier(dCktype: IDCktype): number | undefined {
  return dCktype.id;
}
