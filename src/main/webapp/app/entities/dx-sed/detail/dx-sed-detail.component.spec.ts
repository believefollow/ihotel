import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DxSedDetailComponent } from './dx-sed-detail.component';

describe('Component Tests', () => {
  describe('DxSed Management Detail Component', () => {
    let comp: DxSedDetailComponent;
    let fixture: ComponentFixture<DxSedDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [DxSedDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ dxSed: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(DxSedDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DxSedDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load dxSed on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.dxSed).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
