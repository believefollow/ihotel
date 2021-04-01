import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DxSedinfoDetailComponent } from './dx-sedinfo-detail.component';

describe('Component Tests', () => {
  describe('DxSedinfo Management Detail Component', () => {
    let comp: DxSedinfoDetailComponent;
    let fixture: ComponentFixture<DxSedinfoDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [DxSedinfoDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ dxSedinfo: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(DxSedinfoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DxSedinfoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load dxSedinfo on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.dxSedinfo).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
