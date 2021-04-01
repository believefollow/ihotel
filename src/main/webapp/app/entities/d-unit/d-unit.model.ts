export interface IDUnit {
  id?: number;
  unit?: string;
}

export class DUnit implements IDUnit {
  constructor(public id?: number, public unit?: string) {}
}

export function getDUnitIdentifier(dUnit: IDUnit): number | undefined {
  return dUnit.id;
}
