import * as dayjs from 'dayjs';

export interface IDPdb {
  id?: number;
  begindate?: dayjs.Dayjs | null;
  enddate?: dayjs.Dayjs | null;
  bm?: string | null;
  spmc?: string | null;
  qcsl?: number | null;
  rksl?: number | null;
  xssl?: number | null;
  dbsl?: number | null;
  qtck?: number | null;
  jcsl?: number | null;
  swsl?: number | null;
  pyk?: number | null;
  memo?: string | null;
  depot?: string | null;
  rkje?: number | null;
  xsje?: number | null;
  dbje?: number | null;
  jcje?: number | null;
  dp?: string | null;
  qcje?: number | null;
  swje?: number | null;
  qtje?: number | null;
}

export class DPdb implements IDPdb {
  constructor(
    public id?: number,
    public begindate?: dayjs.Dayjs | null,
    public enddate?: dayjs.Dayjs | null,
    public bm?: string | null,
    public spmc?: string | null,
    public qcsl?: number | null,
    public rksl?: number | null,
    public xssl?: number | null,
    public dbsl?: number | null,
    public qtck?: number | null,
    public jcsl?: number | null,
    public swsl?: number | null,
    public pyk?: number | null,
    public memo?: string | null,
    public depot?: string | null,
    public rkje?: number | null,
    public xsje?: number | null,
    public dbje?: number | null,
    public jcje?: number | null,
    public dp?: string | null,
    public qcje?: number | null,
    public swje?: number | null,
    public qtje?: number | null
  ) {}
}

export function getDPdbIdentifier(dPdb: IDPdb): number | undefined {
  return dPdb.id;
}
