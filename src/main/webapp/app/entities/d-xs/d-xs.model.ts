import * as dayjs from 'dayjs';

export interface IDXs {
  id?: number;
  begintime?: dayjs.Dayjs;
  endtime?: dayjs.Dayjs;
  ckbillno?: string;
  depot?: string | null;
  kcid?: number;
  ckid?: number;
  spbm?: string;
  spmc?: string;
  unit?: string | null;
  rkprice?: number | null;
  xsprice?: number | null;
  sl?: number | null;
  rkje?: number | null;
  xsje?: number | null;
}

export class DXs implements IDXs {
  constructor(
    public id?: number,
    public begintime?: dayjs.Dayjs,
    public endtime?: dayjs.Dayjs,
    public ckbillno?: string,
    public depot?: string | null,
    public kcid?: number,
    public ckid?: number,
    public spbm?: string,
    public spmc?: string,
    public unit?: string | null,
    public rkprice?: number | null,
    public xsprice?: number | null,
    public sl?: number | null,
    public rkje?: number | null,
    public xsje?: number | null
  ) {}
}

export function getDXsIdentifier(dXs: IDXs): number | undefined {
  return dXs.id;
}
