import * as dayjs from 'dayjs';

export interface IDxSed {
  id?: number;
  dxRq?: dayjs.Dayjs | null;
  dxZt?: string | null;
  fsSj?: dayjs.Dayjs | null;
}

export class DxSed implements IDxSed {
  constructor(public id?: number, public dxRq?: dayjs.Dayjs | null, public dxZt?: string | null, public fsSj?: dayjs.Dayjs | null) {}
}

export function getDxSedIdentifier(dxSed: IDxSed): number | undefined {
  return dxSed.id;
}
