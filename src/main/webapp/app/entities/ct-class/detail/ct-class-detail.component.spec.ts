import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CtClassDetailComponent } from './ct-class-detail.component';

describe('Component Tests', () => {
  describe('CtClass Management Detail Component', () => {
    let comp: CtClassDetailComponent;
    let fixture: ComponentFixture<CtClassDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [CtClassDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ ctClass: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(CtClassDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CtClassDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load ctClass on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.ctClass).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
